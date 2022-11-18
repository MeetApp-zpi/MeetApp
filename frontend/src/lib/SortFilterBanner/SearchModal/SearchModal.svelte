<script lang="ts">
    import { slide } from 'svelte/transition';

    import Button from '../../../lib/Button/Button.svelte';
    import execute from '../../../lib/fetchWrapper';

    export let setData;
    export let pageType;

    let inputValue;

    const getTitledPosts = () => {
        let urlParams = new URLSearchParams({ nameContains: inputValue });
        execute(`${pageType}?${urlParams.toString()}`, 'GET')
            .then((r) => r.json())
            .then((r) => setData(r));
    };
</script>

<div class="flex flex-col z-20 w-full py-2 px-4 fixed items-center bg-ivory" transition:slide>
    <input type="text" class="border-grass border-2 my-2 h-8 w-full rounded-lg pl-2" bind:value={inputValue} placeholder="Nazwa" />
    <Button clickHandler={getTitledPosts} class="px-10 py-1">Szukaj</Button>
</div>
