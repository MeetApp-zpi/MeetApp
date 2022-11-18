<script lang="ts">
    import { slide } from 'svelte/transition';

    import Button from '../../../lib/Button/Button.svelte';
    import MultiselectInput from '../../../lib/MultiselectInput/MultiselectInput.svelte';
    import SelectablePill from '../../../lib/SelectablePill/SelectablePill.svelte';
    import execute from '../../../lib/fetchWrapper';

    export let setData;
    export let pageType;

    let categories = [];
    let locations = [];

    let cityValues;
    let selectedCategories = [];

    let promise = execute('categories', 'GET')
        .then((r) => r.json())
        .then((r) => (categories = r));

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

    const clickedCategory = (categoryId: number) => {
        if (selectedCategories.includes(categoryId)) {
            selectedCategories = selectedCategories.filter((id) => id !== categoryId);
        } else {
            selectedCategories.push(categoryId);
        }
    };

    const getFilteredPosts = () => {
        let urlParams = new URLSearchParams();
        for (let categoryId of selectedCategories) {
            urlParams.append('categoryIds', categoryId);
        }
        for (let locationId of cityValues) {
            urlParams.append('locationIds', locationId);
        }

        execute(`${pageType}?` + urlParams.toString(), 'GET')
            .then((r) => r.json())
            .then((r) => setData(r));
    };
</script>

{#await promise then response}
    <div class="flex flex-col z-20 w-full py-2 px-4 fixed items-center bg-ivory" transition:slide>
        <div class="">
            {#each categories as category}
                <SelectablePill isSelected={false} class="px-4 mx-1 my-1" clickCallback={() => clickedCategory(category.id)}>
                    {category.name}
                </SelectablePill>
            {/each}
        </div>
        <div class="my-4 py-3 rounded-lg w-full bg-olive">
            <MultiselectInput
                style="margin-left: 1rem; margin-right: 1rem"
                data={locations}
                placeholder="Miasto"
                inputId="citySelect"
                bind:valueBinder={cityValues}
            />
        </div>
        <div class="mb-2">
            <Button class="px-6" clickHandler={getFilteredPosts}>Filtruj</Button>
        </div>
    </div>
{/await}

<style>
    :global(.sv-content) {
        overflow-x: scroll;
    }
</style>
