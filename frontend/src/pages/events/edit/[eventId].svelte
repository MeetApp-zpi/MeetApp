<script lang="ts">
    import { redirect } from '@roxi/routify';

    import Header from '../../../lib/Header/Header.svelte';
    import PostNameInput from '../../../lib/PostNameInput/PostNameInput.svelte';
    import execute from '../../../lib/fetchWrapper';
    import PostDateInput from '../../../lib/PostDateInput/PostDateInput.svelte';
    import PostTimeInput from '../../../lib/PostTimeInput/PostTimeInput.svelte';
    import PeopleLimitInput from '../../../lib/PeopleLimitInput/PeopleLimitInput.svelte';
    import PostDescription from '../../../lib/PostDescription/PostDescription.svelte';
    import Button from '../../../lib/Button/Button.svelte';
    import MultiselectCategoryInput from '../../../lib/MultiselectCategoryInput/MultiselectCategoryInput.svelte';
    import SelectCityInput from '../../../lib/SelectCityInput/SelectCityInput.svelte';
    import MdAdd from 'svelte-icons/md/MdAdd.svelte';
    import { userDetails } from '../../../lib/stores';

    export let eventId: number;

    let title;
    let initTitle;

    let categories = [];
    let categoryValue = null;

    let cityValue = null;

    let descriptionValue = null;
    let scheduleValue = null;

    let startDateValue = null;
    let startTimeValue = null;
    let startDateTimeErrorMessage = null;
    let startIsoDateTime = null;

    let endDateValue = null;
    let endTimeValue = null;
    let endDateTimeErrorMessage = null;
    let endIsoDateTime = null;

    let endDateAfterStartDateErrorMessage = null;

    let peopleLimitValue = null;

    let image, fileInput;
    let blob;

    if ($userDetails === null) {
        $redirect('/login');
    }

    execute(`users/isAuthor/${eventId}`, 'GET')
        .then((r) => r.text())
        .then((r) => {
            r === 'false' ? $redirect('/events') : null;
        })
        .catch((_) => $redirect('/events'));

    execute('categories', 'GET').then(async (response) => (categories = await response.json()));

    let promise = execute(`events/editDetails/${eventId}`, 'GET')
        .then((r) => r.json())
        .then((r) => {
            descriptionValue = r.description;
            categoryValue = r.categories.map((c) => c.id);
            cityValue = {
                id: r.location.id,
                city: r.location.city.name,
                voivodeship: r.location.voivodeship.name
            };
            initTitle = r.title;
            peopleLimitValue = r.personQuota === null ? null : r.personQuota;
            scheduleValue = r.schedule;
            startDateValue = r.startDateTime.date.split('.').reverse().join('-');
            startTimeValue = r.startDateTime.time;
            endDateValue = r.endDateTime.date.split('.').reverse().join('-');
            endTimeValue = r.endDateTime.time;
            image = r.picture === null ? undefined : r.picture;
        });

    const validateCategory = () => {
        let errorMsg = document.getElementById('categoryErrorMsg');
        if (categoryValue === null || categoryValue.length === 0) {
            errorMsg.classList.remove('hidden');
            return false;
        }
        errorMsg.className += ' hidden';
        return true;
    };

    const validateCity = () => {
        let errorMsg = document.getElementById('cityErrorMsg');
        let svControl = document.getElementById('cityInputBox').children[0].children[0];
        if (cityValue === null) {
            errorMsg.classList.remove('hidden');
            errorMsg.className += ' block';
            svControl.className += ' !border-red-500';
            return false;
        }
        errorMsg.classList.remove('block');
        errorMsg.className += ' hidden';
        svControl.classList.remove('!border-red-500');
        return true;
    };

    const validateDateTime = () => {
        if (startDateValue !== null && startTimeValue !== null) {
            let startDate = new Date(startDateValue);
            const [hours, minutes] = startTimeValue.split(':');
            startDate.setUTCHours(hours - 1);
            startDate.setUTCMinutes(minutes);

            const currentDate = new Date();
            if (startDate > currentDate) {
                if (startDateTimeErrorMessage !== null) {
                    startDateTimeErrorMessage.className += ' hidden';
                }

                startDate.setUTCHours(startDate.getUTCHours() + 1); // it's complicated
                startIsoDateTime = startDate.toISOString();

                if (endDateValue !== null && endTimeValue !== null) {
                    let endDate = new Date(endDateValue);
                    const [hours, minutes] = endTimeValue.split(':');
                    endDate.setUTCHours(hours - 1);
                    endDate.setUTCMinutes(minutes);

                    const currentDate = new Date();
                    if (endDate > currentDate) {
                        if (endDateTimeErrorMessage !== null) {
                            endDateTimeErrorMessage.className += ' hidden';
                        }

                        endDate.setUTCHours(endDate.getUTCHours() + 1); // it's complicated
                        endIsoDateTime = endDate.toISOString();

                        if (endDate.getTime() - startDate.getTime() <= 0) {
                            if (endDateAfterStartDateErrorMessage !== null) {
                                endDateAfterStartDateErrorMessage.classList.remove('hidden');
                            }

                            return false;
                        }

                        if (endDateAfterStartDateErrorMessage !== null) {
                            endDateAfterStartDateErrorMessage.className += ' hidden';
                        }

                        return true;
                    }
                }

                if (endDateTimeErrorMessage !== null) {
                    endDateTimeErrorMessage.classList.remove('hidden');
                }

                return false;
            }
        }

        if (startDateTimeErrorMessage !== null) {
            startDateTimeErrorMessage.classList.remove('hidden');
        }

        return false;
    };

    const validateDescription = () => {
        let errorMessage = document.getElementById('descriptionErrorMsg');
        if (descriptionValue === null || descriptionValue.length < 1 || descriptionValue.length > 10000) {
            errorMessage.classList.remove('hidden');
            return false;
        }
        errorMessage.className += ' hidden';
        return true;
    };

    const validateSchedule = () => {
        let errorMessage = document.getElementById('scheduleErrorMsg');
        if (scheduleValue === null || scheduleValue.length < 1 || scheduleValue.length > 5000) {
            errorMessage.classList.remove('hidden');
            return false;
        }
        errorMessage.className += ' hidden';
        return true;
    };

    const validatePeopleLimit = () => {
        let errorMessage = document.getElementById('peopleLimitErrorMsg');
        if (peopleLimitValue === null || peopleLimitValue <= 0) {
            errorMessage.classList.remove('hidden');
            return false;
        }
        errorMessage.className += ' hidden';
        return true;
    };

    const handleSubmit = async () => {
        if (
            title.getIsValid() &&
            validateCategory() &&
            validateCity() &&
            validateDateTime() &&
            validatePeopleLimit() &&
            validateDescription() &&
            validateSchedule()
        ) {
            let multipartImage = new FormData();
            multipartImage.append('locationId', cityValue.id);
            multipartImage.append('title', title.getPostName());
            multipartImage.append('description', descriptionValue);
            multipartImage.append('schedule', scheduleValue);
            multipartImage.append('categoryIds', categoryValue);
            multipartImage.append('startDate', startIsoDateTime);
            multipartImage.append('endDate', endIsoDateTime);
            multipartImage.append('personQuota', peopleLimitValue);
            multipartImage.append('picture', blob);

            await fetch(`http://meetapp.eastus.cloudapp.azure.com:8080/api/events/${eventId}`, {
                method: 'PUT',
                body: multipartImage
            }).then(() => $redirect('/events'));
        }
    };

    const onFileSelected = (e) => {
        blob = e.target.files[0];
        let reader = new FileReader();
        try {
            reader.readAsDataURL(blob);
            reader.onload = (e) => {
                image = e.target.result;
            };
        } catch (e) {
            console.error(e);
        }
    };
</script>

<div class="h-screen">
    <Header />
    {#await promise then _}
        <div class="flex flex-col h-[calc(100%-4rem)] lg:w-1/3 lg:mx-auto overflow-auto justify-between items-center bg-ivory">
            <div class="w-full">
                <PostNameInput placeholder="Nazwa wydarzenia" bind:this={title} maxLength={100} bind:postName={initTitle} />

                <p class="mx-1.5 mb-1 text-lg text-pine">Zdjęcie wydarzenia</p>
                <div class="flex justify-center">
                    {#if image !== undefined}
                        <div class="mx-8 aspect-square w-full rounded-2xl bg-white flex justify-center text-center text-pickle flex-col">
                            <img class="rounded-2xl" src={image} />
                            <div
                                class="my-2 hover:cursor-pointer"
                                on:click={() => {
                                    fileInput.click();
                                }}
                            >
                                Zmień zdjęcie
                            </div>
                        </div>
                    {:else}
                        <div
                            class="mx-14 aspect-square w-full border-pickle rounded-2xl border-2 bg-white flex justify-center text-center text-pickle flex-col hover:cursor-pointer"
                            on:click={() => {
                                fileInput.click();
                            }}
                        >
                            <div class="h-12 w-12 ml-auto mr-auto">
                                <MdAdd />
                            </div>
                            Dodaj zdjęcie
                        </div>
                    {/if}
                    <input style="display:none" type="file" accept=".jpg, .jpeg, .png" on:change={(e) => onFileSelected(e)} bind:this={fileInput} />
                </div>

                <div class="mx-1.5 mt-2" id="categoryInputBox">
                    <MultiselectCategoryInput
                        style=""
                        data={categories}
                        placeholder="Kategoria"
                        inputId="categorySelect"
                        bind:selected={categoryValue}
                    />
                </div>
                <p class="text-red-500 text-sm mt-1 mx-8 hidden" id="categoryErrorMsg">Musisz wybrać kategorię</p>

                <div class="bg-tea mx-1.5 my-4 p-2 rounded-lg">
                    <div id="cityInputBox" class="pb-2">
                        <SelectCityInput
                            fetch="http://meetapp.eastus.cloudapp.azure.com:8080/api/locations?nameSearch=[query]"
                            placeholder="Miasto"
                            inputId="citySelect"
                            bind:selected={cityValue}
                        />
                        <p class="text-red-500 text-sm mx-4 hidden" id="cityErrorMsg">Musisz wybrać miasto</p>
                    </div>

                    <div class="flex flex-col mt-2 text-pine">
                        <p class="text-lg">Data rozpoczęcia</p>
                        <div class="flex flex-row">
                            <div class="py-1 mr-0.5 object-left flex-1">
                                <PostDateInput bind:value={startDateValue} />
                            </div>
                            <div class="py-1 ml-0.5 object-right flex-1">
                                <PostTimeInput bind:value={startTimeValue} />
                            </div>
                        </div>
                    </div>
                    <p class="text-red-500 text-sm mx-2 hidden mb-2" bind:this={startDateTimeErrorMessage}>Data musi być w przyszłości</p>

                    <div class="flex flex-col mt-2 text-pine">
                        <p class="text-lg">Data zakończenia</p>
                        <div class="flex flex-row">
                            <div class="py-1 mr-0.5 object-left flex-1">
                                <PostDateInput bind:value={endDateValue} />
                            </div>
                            <div class="py-1 ml-0.5 object-right flex-1">
                                <PostTimeInput bind:value={endTimeValue} />
                            </div>
                        </div>
                    </div>
                    <p class="text-red-500 text-sm mx-2 hidden mb-2" bind:this={endDateTimeErrorMessage}>Data musi być w przyszłości</p>
                    <p class="text-red-500 text-sm mx-2 hidden mb-2" bind:this={endDateAfterStartDateErrorMessage}>
                        Data zakończenia musi być po dacie rozpoczęcia
                    </p>

                    <div class="mt-4">
                        <PeopleLimitInput bind:value={peopleLimitValue} />
                    </div>
                    <p class="hidden peer-invalid:block text-red-500 text-sm my-2" id="peopleLimitErrorMsg">Limit osób musi być dodatni</p>
                </div>

                <PostDescription placeholder="Opis" bind:value={descriptionValue} maxLength={10000} />
                <p class="hidden peer-invalid:block text-red-500 text-sm mx-8 mb-2" id="descriptionErrorMsg">Opis nie może być pusty</p>

                <PostDescription placeholder="Harmonogram" bind:value={scheduleValue} maxLength={5000} />
                <p class="hidden peer-invalid:block text-red-500 text-sm mx-8 mb-2" id="scheduleErrorMsg">Harmonogram nie może być pusty</p>
            </div>
            <div class="">
                <Button class="px-6 py-1 mt-2 mb-4 text-xl" clickHandler={handleSubmit}>Stwórz spotkanie</Button>
            </div>
        </div>
    {/await}
</div>
