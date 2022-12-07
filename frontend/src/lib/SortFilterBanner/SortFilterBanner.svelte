<script lang="ts">
    import { fade } from 'svelte/transition';

    import FaSearch from 'svelte-icons/fa/FaSearch.svelte';
    import MdDelete from 'svelte-icons/md/MdDelete.svelte';

    import FilterModal from './FilterModal/FilterModal.svelte';
    import SearchModal from './SearchModal/SearchModal.svelte';
    import SortModal from './SortModal/SortModal.svelte';
    import { filteredCategoryIds, filteredLocationIds, sortingOption, nameSearchParam, clearFilters } from '../stores';

    export let sortOptions;

    let isFilterOpen: boolean = false;
    let isSortOpen: boolean = false;
    let isSearchOpen: boolean = false;
    let isAnyFiltering: boolean = false;

    const switchFilterModal = () => {
        isFilterOpen = !isFilterOpen;
        isSortOpen = false;
        isSearchOpen = false;
    };

    const switchSortModal = () => {
        isSortOpen = !isSortOpen;
        isFilterOpen = false;
        isSearchOpen = false;
    };

    const switchSearchModal = () => {
        isSearchOpen = !isSearchOpen;
        isFilterOpen = false;
        isSortOpen = false;
    };

    const clearModals = () => {
        isFilterOpen = false;
        isSortOpen = false;
        isSearchOpen = false;
    };

    $: {
        if ($filteredCategoryIds.length > 0 || $filteredLocationIds.length > 0 || $sortingOption !== 1 || $nameSearchParam !== null) {
            isAnyFiltering = true;
        } else {
            isAnyFiltering = false;
        }
    }
</script>

<div class="flex justify-center bg-pickle h-8 text-ivory font-bold lg:hidden">
    <button on:click={switchFilterModal}>Filtry</button>
    <button on:click={switchSortModal} class="mx-4">Sortuj</button>
    <button on:click={switchSearchModal}>
        <div class="w-5">
            <FaSearch />
        </div>
    </button>
    {#if isAnyFiltering}
        <button on:click={clearFilters}>
            <div class="ml-2 w-6">
                <MdDelete />
            </div>
        </button>
    {/if}
</div>
{#if isFilterOpen}
    <div class="">
        <FilterModal modalCloser={clearModals} />
        <div on:click={clearModals} on:keydown={clearModals} class="bg-black bg-opacity-40 fixed h-full w-full z-10" transition:fade />
    </div>
{:else if isSortOpen}
    <div class="">
        <SortModal options={sortOptions} modalCloser={clearModals} />
        <div on:click={clearModals} on:keydown={clearModals} class="bg-black bg-opacity-40 fixed h-full w-full z-10" transition:fade />
    </div>
{:else if isSearchOpen}
    <div class="">
        <SearchModal modalCloser={clearModals} />
        <div on:click={clearModals} on:keydown={clearModals} class="bg-black bg-opacity-40 fixed h-full w-full z-10" transition:fade />
    </div>
{/if}
