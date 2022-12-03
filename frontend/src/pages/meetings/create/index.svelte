<script lang="ts">
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

    let title;

    let categories = [];
    let categoryValue = null;

    let cityValue = null;

    let descriptionValue = null;

    let dateValue = null;
    let timeValue = null;
    let dateTimeErrorMessage = null;
    let isoDateTime = null;

    let peopleLimitValue = null;

    execute('categories', 'GET').then(async (response) => (categories = await response.json()));

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
        if (dateValue !== null && timeValue !== null) {
            let date = new Date(dateValue);
            const [hours, minutes] = timeValue.split(':');
            date.setUTCHours(hours - 1);
            date.setUTCMinutes(minutes);

            const currentDate = new Date();
            if (date > currentDate) {
                if (dateTimeErrorMessage !== null) {
                    dateTimeErrorMessage.className += ' hidden';
                }

                date.setUTCHours(date.getUTCHours() + 1) // it's complicated
                isoDateTime = date.toISOString();
                return true;
            }
        }

        if (dateTimeErrorMessage !== null) {
            dateTimeErrorMessage.classList.remove('hidden');
        }
        return false;
    };

    const validateDescription = () => {
        let errorMessage = document.getElementById('descriptionErrorMsg');
        if (descriptionValue === null || descriptionValue.length < 1 || descriptionValue.length > 200) {
            errorMessage.classList.remove('hidden');
            return false;
        }
        errorMessage.className += ' hidden';
        return true;
    };

    const handleSubmit = () => {
        if (title.getIsValid() && validateCategory() && validateCity() && validateDateTime() && validateDescription()) {
            let requestBody = {
                locationId: cityValue.id,
                title: title.getPostName(),
                description: descriptionValue,
                categoryIds: categoryValue,
                meetingDate: isoDateTime,
                personQuota: peopleLimitValue
            };
            execute('meetings', 'POST', requestBody).then((r) => (window.location.href = 'http://localhost:5173/meetings'));
        }
    };
</script>

<div class="h-screen">
    <Header />
    <div class="flex flex-col h-[calc(100%-4rem)] overflow-auto justify-between items-center bg-ivory">
        <div class="w-full">
            <PostNameInput postname="Nazwa spotkania" bind:this={title} maxLength="50" />
            <div class="mx-1.5 mt-2" id="categoryInputBox">
                <MultiselectCategoryInput style="" data={categories} placeholder="Kategoria" inputId="categorySelect" bind:selected={categoryValue} />
            </div>
            <p class="text-red-500 text-sm mt-1 mx-8 hidden" id="categoryErrorMsg">Musisz wybrać kategorię</p>
            <div class="bg-tea mx-1.5 my-4 p-2 rounded-lg">
                <div id="cityInputBox">
                    <SelectCityInput
                        fetch="http://localhost:5173/api/locations?nameSearch=[query]"
                        placeholder="Miasto"
                        inputId="citySelect"
                        bind:selected={cityValue}
                    />
                    <p class="text-red-500 text-sm mx-4 hidden" id="cityErrorMsg">Musisz wybrać miasto</p>
                </div>
                <div class="flex">
                    <div class="py-2 mr-0.5 object-left flex-1">
                        <PostDateInput bind:value={dateValue} />
                    </div>
                    <div class="py-2 ml-0.5 object-right flex-1">
                        <PostTimeInput bind:value={timeValue} />
                    </div>
                </div>
                <p class="text-red-500 text-sm mx-2 hidden mb-2" bind:this={dateTimeErrorMessage}>Data musi być w przyszłości</p>
                <PeopleLimitInput bind:value={peopleLimitValue} />
            </div>
            <PostDescription bind:value={descriptionValue} maxLength="250" />
            <p class="hidden peer-invalid:block text-red-500 text-sm mx-8 mb-2" id="descriptionErrorMsg">Opis nie może być pusty</p>
        </div>
        <div class="">
            <Button class="px-6 py-1 mt-2 mb-4 text-xl" clickHandler={handleSubmit}>Stwórz spotkanie</Button>
        </div>
    </div>
</div>
