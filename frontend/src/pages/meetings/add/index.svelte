<script lang="ts">
    import Svelecte, { addFormatter } from 'svelecte';
    import Header from '../../../lib/Header/Header.svelte';
    import PostNameInput from '../../../lib/PostNameInput/PostNameInput.svelte';
    import execute from '../../../lib/fetchWrapper';
    import CityInput from '../../../lib/CityInput/CityInput.svelte';
    import PostDateInput from '../../../lib/PostDateInput/PostDateInput.svelte';
    import PostTimeInput from '../../../lib/PostTimeInput/PostTimeInput.svelte';
    import PeopleLimitInput from '../../../lib/PeopleLimitInput/PeopleLimitInput.svelte';
    import PostDescription from '../../../lib/PostDescription/PostDescription.svelte';
    import Button from '../../../lib/Button/Button.svelte';

    let cityValue = null;
    let titleValue = null;
    let descriptionValue = null;

    let titleInput = null;
    let descriptionInput = null;

    let locations = [];

    function cityRenderer(item, isSelected) {
        return `${item.city}<span class='text-gray'>, ${item.voivodeship}</span>`;
    }

    addFormatter('city-render', cityRenderer);

    execute('locations', 'GET')
        .then((r) => r.json())
        .then((r) => {
            for (const [index, location] of r.entries()) {
                locations = [
                    ...locations,
                    {
                        id: location.id,
                        name: location.city.name + ', ' + location.voivodeship.name,
                        city: location.city.name,
                        voivodeship: location.voivodeship.name,
                        sortNum: index
                    }
                ];
            }
        });

    const validateTitle = () => {
        if (titleValue === null || titleValue.length < 5 || titleValue.length > 50) {
            titleInput.setCustomValidity('Tytuł musi mieć między 5 a 50 znaków');
            return false;
        }
        titleInput.setCustomValidity('');
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
        if (descriptionValue === null || descriptionValue.length < 1 || descriptionValue.length > 200) {
            descriptionInput.setCustomValidity('Opis musi mieć między 1 a 200 znaków');
            return false;
        }
        descriptionInput.setCustomValidity('');
        return true;
    };

    const handleSubmit = () => {
        if (validateTitle() && validateCity() && validateDescription()) {
            let requestBody = {
                locationId: cityValue,
                title: titleValue,
                description: descriptionValue,
            };

            execute('meetings', 'POST', requestBody).then((r) => (window.location.href = 'http://localhost:5173'));
        }
    };
</script>

<div class="h-screen">
    <Header />
    <div class="flex flex-col h-[calc(100%-4rem)] overflow-auto justify-between items-center bg-ivory">
        <form>
            <PostNameInput />
            <div class="bg-tea m-4 p-2 rounded-lg">
                <CityInput />
                <div class="flex">
                    <div class="py-2 mr-1 object-left flex-1">
                        <PostDateInput />
                    </div>
                    <div class="py-2 ml-1 object-right flex-1">
                        <PostTimeInput />
                    </div>
                </div>
                <PeopleLimitInput />
            </div>
            <PostDescription />
        </form>
        <div class="">
            <Button class="px-6 py-1 mt-2 mb-4 text-xl" clickHandler={handleSubmit}>Stwórz spotkanie</Button>
        </div>
    </div>
</div>