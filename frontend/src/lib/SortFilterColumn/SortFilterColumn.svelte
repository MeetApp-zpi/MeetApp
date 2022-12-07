<script lang="ts">
    import Button from '../Button/Button.svelte';
    import FilterModal from './FilterModal/FilterModal.svelte';
    import SearchModal from './SearchModal/SearchModal.svelte';
    import SortModal from './SortModal/SortModal.svelte';
    import { filteredCategoryIds, filteredLocationIds, sortingOption, nameSearchParam, userDetails, clearFilters } from '../stores';

    export let sortOptions;

    let isAnyFiltering: boolean = false;

    let categories;
    let cities;
    let sorting;
    let searchInput;

    const setUserInterests = () => {
        categories = $userDetails.interests.map((i) => i.id);
    };

    const setUrlParams = () => {
        $filteredCategoryIds = categories;
        $filteredLocationIds = cities;
        $sortingOption = sorting;
        $nameSearchParam = searchInput;
    };

    const clearAllFilters = () => {
        clearFilters();
        categories = $filteredCategoryIds;
        cities = $filteredLocationIds;
        sorting = $sortingOption;
        searchInput = $nameSearchParam;
    };

    $: {
        if ($filteredCategoryIds.length > 0 || $filteredLocationIds.length > 0 || $sortingOption !== 1 || $nameSearchParam !== null) {
            isAnyFiltering = true;
        } else {
            isAnyFiltering = false;
        }
    }
</script>

<div class="">
    <div class="mb-2">
        <div on:click={setUserInterests} on:keydown={setUserInterests} class="hover:cursor-pointer text-pine text-center font-bold px-4 py-1 mt-2">
            Zaznacz moje zainteresowania
        </div>
    </div>
    <FilterModal bind:selectedCategories={categories} bind:cityValues={cities} />
    <SortModal options={sortOptions} bind:selectedSorting={sorting} />
    <SearchModal bind:inputValue={searchInput} />
    <div class="flex flex-row justify-between">
        {#if isAnyFiltering}
            <Button class="bg-orange flex-1 mx-2 px-2 py-1" clickHandler={clearAllFilters}>Wyczyść filtry</Button>
        {:else}
            <div class="" />
        {/if}
        <Button class="{isAnyFiltering ? 'px-2 mx-2' : 'px-10 mx-14'} flex-1 py-1" clickHandler={setUrlParams}>Zatwierdź</Button>
        <div class="" />
    </div>
</div>
