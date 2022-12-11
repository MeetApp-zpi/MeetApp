<script lang="ts">
    import { redirect } from '@roxi/routify';
    import MdInfoOutline from 'svelte-icons/md/MdInfoOutline.svelte';

    import Button from '../../../lib/Button/Button.svelte';
    import Header from '../../../lib/Header/Header.svelte';
    import MultiselectCategoryInput from '../../../lib/MultiselectCategoryInput/MultiselectCategoryInput.svelte';
    import execute from '../../../lib/fetchWrapper';
    import SelectCityInput from '../../../lib/SelectCityInput/SelectCityInput.svelte';
    import PostNameInput from '../../../lib/PostNameInput/PostNameInput.svelte';
    import PostDescription from '../../../lib/PostDescription/PostDescription.svelte';
    import { userDetails } from '../../../lib/stores';

    let title = null;

    let categoryValue = null;
    let cityValue = null;
    let descriptionValue = null;

    let categories = [];

    if ($userDetails === null) {
        $redirect('/login');
    }

    execute('categories', 'GET')
        .then((r) => r.json())
        .then((r) => (categories = r));

    const validateCategory = () => {
        let errorMsg = document.getElementById('categoryErrorMsg');
        let svControl = document.getElementById('categoryInputBox').children[0].children[0];
        if (categoryValue === null || categoryValue.length === 0) {
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
        if (title.getIsValid() && validateCategory() && validateCity() && validateDescription()) {
            let requestBody = {
                locationId: cityValue.id,
                title: title.getPostName(),
                description: descriptionValue,
                categoryIds: categoryValue
            };
            execute('announcements', 'POST', requestBody).then((r) => $redirect('/announcements'));
        }
    };
</script>

<div class="h-screen">
    <Header />
    <div class="flex flex-col lg:w-1/3 lg:mx-auto h-[calc(100%-4rem)] overflow-auto justify-between items-center bg-ivory">
        <div class="w-full">
            <PostNameInput placeholder="Nazwa ogłoszenia" bind:this={title} maxLength={50} />
            <div class="mx-1.5 mt-2 categorySvelecteBox" id="categoryInputBox">
                <MultiselectCategoryInput style="" data={categories} placeholder="Kategoria" inputId="categorySelect" bind:selected={categoryValue} />
            </div>
            <p class="text-red-500 text-sm mt-1 mx-4 hidden" id="categoryErrorMsg">Musisz wybrać kategorię</p>
            <div class="bg-tea mx-1.5 my-4 p-2 rounded-lg rounded-xl" id="cityInputBox">
                <SelectCityInput
                    fetch="http://localhost:5173/api/locations?nameSearch=[query]"
                    placeholder="Miasto"
                    inputId="citySelect"
                    bind:selected={cityValue}
                />
                <p class="text-red-500 text-sm mx-4 hidden" id="cityErrorMsg">Musisz wybrać miasto</p>
            </div>
            <div class="">
                <PostDescription bind:value={descriptionValue} maxLength={200} placeholder="Opis" />
                <p class="hidden peer-invalid:block text-red-500 text-sm mx-8 mb-2" id="descriptionErrorMsg">Opis nie może być pusty</p>
            </div>
            <div class="flex flex-row text-cocoa items-center mx-8 my-4">
                <div class="w-10 mx-2">
                    <MdInfoOutline />
                </div>
                <p class="text-sm">Twoje ogłoszenie wygaśnie miesiąc po opublikowaniu</p>
            </div>
        </div>
        <div class="" data-cy="submitAnn">
            <Button class="px-6 py-1 mt-2 mb-4 text-xl" clickHandler={handleSubmit}>Stwórz ogłoszenie</Button>
        </div>
    </div>
</div>
