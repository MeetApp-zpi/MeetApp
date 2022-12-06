<script lang="ts">
    import Button from '../Button/Button.svelte';
    import FilterModal from './FilterModal/FilterModal.svelte';
    import SearchModal from './SearchModal/SearchModal.svelte';
    import SortModal from './SortModal/SortModal.svelte';
    import { filteredCategoryIds, filteredLocationIds, sortingOption, nameSearchParam } from '../stores';

    export let sortOptions;

    let categories;
    let cities;
    let sorting;
    let searchInput;

    const setUrlParams = () => {
        console.log(cities);
        $filteredCategoryIds = categories;
        $filteredLocationIds = cities;
        $sortingOption = sorting;
        $nameSearchParam = searchInput;
    };
</script>

<div class="">
    <FilterModal bind:selectedCategories={categories} bind:cityValues={cities} />
    <SortModal options={sortOptions} bind:selectedSorting={sorting} />
    <SearchModal bind:inputValue={searchInput} />
    <div class="flex flex-row justify-between">
        <div class="" />
        <Button class="px-10 py-1" clickHandler={setUrlParams}>Zatwierdź</Button>
        <div class="" />
    </div>
</div>
