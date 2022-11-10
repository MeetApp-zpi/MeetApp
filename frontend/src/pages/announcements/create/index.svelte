<script lang="ts">
    import Svelecte, { addFormatter } from 'svelecte';
    import MdInfoOutline from 'svelte-icons/md/MdInfoOutline.svelte';

    import Button from '../../../lib/Button/Button.svelte';
    import Header from '../../../lib/Header/Header.svelte';
    import execute from '../../../lib/fetchWrapper';

    let cityValue = null;
    let locations = [];

    function cityRenderer(item, isSelected) {
        return `${item.city}<span class='text-gray'>, ${item.voivodeship}</span>`;
    }

    addFormatter('city-render', cityRenderer);

    execute('locations', 'GET')
        .then((r) => r.json())
        .then((r) => {
            for (const location of r) {
                locations = [
                    ...locations,
                    {
                        id: location.id,
                        name: location.city.name + ', ' + location.voivodeship.name,
                        city: location.city.name,
                        voivodeship: location.voivodeship.name
                    }
                ];
            }
            locations = locations.sort((a, b) => {
                if (a.name < b.name) {
                    return -1;
                } else if (a.name > b.name) {
                    return 1;
                } else {
                    return 0;
                }
            });
        });

    $: console.log(locations);
</script>

<div class="h-screen">
    <Header />
    <div class="flex flex-col h-[90%] overflow-auto justify-between items-center">
        <div class="">
            <div class="mx-4">
                <input
                    class="border-grass border-2 rounded-lg w-full px-4 py-1 mr-2 my-2 focus:outline-none"
                    type="text"
                    placeholder="Nazwa ogłoszenia"
                />
            </div>
            <div class="">
                <Svelecte
                    sortField="id"
                    searchField="city"
                    renderer="city-render"
                    options={locations}
                    placeholder="Miasto"
                    inputId="citySelect"
                    bind:value={cityValue}
                />
            </div>
            <div class="mx-4">
                <textarea class="px-4 py-1 my-2 border-grass border-2 rounded-lg w-full h-40 focus:outline-none" placeholder="Opis" />
            </div>
            <div class="flex flex-row text-cocoa items-center mx-8">
                <div class="w-10 mx-2">
                    <MdInfoOutline />
                </div>
                <p class="text-sm">Twoje ogłoszenie wygaśnie miesiąc po opublikowaniu</p>
            </div>
        </div>
        <div class="">
            <Button class="px-6 py-1 mb-4 text-sm" clickHandler={() => null}>Stwórz ogłoszenie</Button>
        </div>
    </div>
</div>
