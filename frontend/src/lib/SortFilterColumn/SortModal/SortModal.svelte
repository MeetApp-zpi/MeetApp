<script lang="ts">
    import { slide } from 'svelte/transition';

    import { sortingOption } from '../../stores';

    export let options;

    export let selectedSorting = $sortingOption;
</script>

<div class="flex flex-col w-full py-2 px-4 items-center bg-green-mist" transition:slide>
    {#each options as item}
        <div class="flex flex-row w-full h-10 items-center">
            <input
                class="w-8 h-8 appearance-none rounded-full border-4 border-tea bg-ivory
            checked:bg-grass checked:border-4 checked:border-pine
            focus:ring-tea focus:ring-4 mr-2"
                type="radio"
                name="sortOption"
                id="option-{item.id}"
                value={item.id}
                bind:group={selectedSorting}
            />
            <label class="text-cocoa font-bold" for="option-{item.id}">{item.name}</label>
        </div>
    {/each}
</div>
