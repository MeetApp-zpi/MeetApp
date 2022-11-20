<script lang="ts">
    import { slide } from 'svelte/transition';

    import Button from '../../../lib/Button/Button.svelte';
    import { nameSearchParam } from '../../stores';

    export let modalCloser;

    let inputValue = $nameSearchParam;

    const setTitledPosts = () => {
        $nameSearchParam = inputValue;
        modalCloser();
    };
</script>

<div class="flex flex-col z-20 w-full py-2 px-4 fixed items-center bg-ivory" transition:slide>
    <input type="text" class="border-grass border-2 py-6 text-lg my-2 h-8 w-full rounded-lg pl-2" bind:value={inputValue} placeholder="Nazwa" />
    <Button clickHandler={setTitledPosts} class="px-10 py-1">Szukaj</Button>
</div>
