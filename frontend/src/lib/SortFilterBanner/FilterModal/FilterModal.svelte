<script lang="ts">
    import { slide } from 'svelte/transition';

    import Button from '../../../lib/Button/Button.svelte';
    import MultiselectCityInput from '../../MultiselectCityInput/MultiselectCityInput.svelte';
    import SelectablePill from '../../Pill/SelectablePill.svelte';
    import execute from '../../../lib/fetchWrapper';
    import { filteredCategoryIds, filteredLocationIds, userDetails } from '../../stores';

    export let modalCloser;

    let categories = [];

    let cityValues = $filteredLocationIds;
    let selectedCategories = $filteredCategoryIds;

    let promise = execute('categories', 'GET')
        .then((r) => r.json())
        .then((r) => (categories = r));

    const setUserInterests = () => {
        selectedCategories = $userDetails.interests.map((i) => i.id);
    };

    const clickedCategory = (categoryId: number) => {
        if (selectedCategories.includes(categoryId)) {
            selectedCategories = selectedCategories.filter((id) => id !== categoryId);
        } else {
            selectedCategories.push(categoryId);
        }
    };

    const setFilteredPosts = () => {
        console.log(cityValues);
        $filteredCategoryIds = selectedCategories;
        $filteredLocationIds = cityValues;
        modalCloser();
    };
</script>

{#await promise then _}
    <div class="flex flex-col z-20 w-full py-2 px-4 fixed items-center bg-ivory" transition:slide>
        <div class="mb-2">
            <div on:click={setUserInterests} on:keydown={setUserInterests} class="hover:cursor-pointer text-pine font-bold px-4 py-1 mt-2">
                Zaznacz moje zainteresowania
            </div>
        </div>
        <div class="">
            {#each categories as category}
                <SelectablePill
                    isSelected={selectedCategories.includes(category.id)}
                    class="px-4 mx-1 my-1"
                    clickCallback={() => clickedCategory(category.id)}
                >
                    {category.name}
                </SelectablePill>
            {/each}
        </div>
        <div class="my-4 py-3 rounded-lg w-full bg-olive">
            <MultiselectCityInput
                style="margin-left: 1rem; margin-right: 1rem"
                fetch="http://meetapp.eastus.cloudapp.azure.com:8080/api/locations?nameSearch=[query]"
                placeholder="Miasto"
                inputId="citySelect"
                bind:selected={cityValues}
            />
        </div>
        <div class="mb-2">
            <Button class="px-10 py-1 mt-2" clickHandler={setFilteredPosts}>Filtruj</Button>
        </div>
    </div>
{/await}

<style>
    :global(.sv-content) {
        overflow-x: scroll;
    }
</style>
