<script lang="ts">
    import { slide } from 'svelte/transition';

    import MultiselectCityInput from '../../MultiselectCityInput/MultiselectCityInput.svelte';
    import SelectablePill from '../../Pill/SelectablePill.svelte';
    import execute from '../../../lib/fetchWrapper';
    import { filteredCategoryIds, filteredLocationIds } from '../../stores';

    let categories = [];

    export let cityValues = $filteredLocationIds;
    export let selectedCategories = $filteredCategoryIds;

    let promise = execute('categories', 'GET')
        .then((r) => r.json())
        .then((r) => (categories = r));

    const clickedCategory = (categoryId: number) => {
        if (selectedCategories.includes(categoryId)) {
            selectedCategories = selectedCategories.filter((id) => id !== categoryId);
        } else {
            selectedCategories.push(categoryId);
        }
    };
</script>

{#await promise then _}
    <div class="flex flex-col w-full py-2 px-4 items-center bg-green-mist" transition:slide>
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
    </div>
{/await}
