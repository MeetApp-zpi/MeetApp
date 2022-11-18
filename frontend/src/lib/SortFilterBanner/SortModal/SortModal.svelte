<script lang="ts">
    import { slide } from 'svelte/transition';

    import Button from '../../../lib/Button/Button.svelte';
    import execute from '../../../lib/fetchWrapper';

    export let options;
    export let setData;
    export let pageType;

    let selectedSorting;

    const getSortedPosts = () => {
        let urlParams = new URLSearchParams({ sortMethod: selectedSorting });
        execute(`${pageType}?${urlParams.toString()}`, 'GET')
            .then((r) => r.json())
            .then((r) => setData(r));
    };
</script>

<div class="flex flex-col z-20 w-full py-2 px-4 fixed items-center bg-ivory" transition:slide>
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
    <Button class="px-10 py-1 mt-2" clickHandler={getSortedPosts}>Sortuj</Button>
</div>
