<script lang="ts">
    import { fade } from 'svelte/transition';

    import FaSearch from 'svelte-icons/fa/FaSearch.svelte';
    import FilterModal from './FilterModal/FilterModal.svelte';

    let isFilterOpen: boolean = false;

    const switchFilterModal = () => {
        isFilterOpen = !isFilterOpen;
    };

    const clearModals = () => {
        isFilterOpen = false;
    };

    $: console.log(isFilterOpen);
</script>

<div class="flex justify-center bg-pickle h-8 text-ivory font-bold">
    <button on:click={switchFilterModal}>Filtry</button>
    <button class="mx-4">Sortuj</button>
    <button>
        <div class="w-5">
            <FaSearch />
        </div>
    </button>
</div>
{#if isFilterOpen}
    <div class="">
        <FilterModal />
        <div on:click={clearModals} on:keydown={clearModals} class="bg-black bg-opacity-40 fixed h-full w-full z-10" transition:fade />
    </div>
{/if}
