<script lang="ts">
    import { slide } from 'svelte/transition';

    import { nameSearchParam } from '../../stores';

    export let inputValue = $nameSearchParam;
</script>

<div class="flex flex-col w-full py-2 px-4 items-center bg-green-mist" transition:slide>
    <input type="text" class="border-grass border-2 py-6 text-lg my-2 h-8 w-full rounded-lg pl-2" bind:value={inputValue} placeholder="Nazwa" />
</div>
