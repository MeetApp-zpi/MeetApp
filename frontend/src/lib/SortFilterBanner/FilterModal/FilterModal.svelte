<script lang="ts">
    import { slide } from 'svelte/transition';

    import Button from '../../../lib/Button/Button.svelte';
    import SelectablePill from '../../../lib/SelectablePill/SelectablePill.svelte';
    import execute from '../../../lib/fetchWrapper';

    let categories = [];

    let promise = execute('categories', 'GET')
        .then((r) => r.json())
        .then((r) => (categories = r));
</script>

{#await promise then response}
    <div class="flex flex-col z-20 w-full py-2 fixed items-center bg-ivory" transition:slide>
        <div class="">
            {#each categories as category}
                <SelectablePill isSelected={false} class="px-4 mx-2 my-1" clickCallback={() => null}>
                    {category.name}
                </SelectablePill>
            {/each}
        </div>
        <div class="">Miasto</div>
        <div class="">
            <Button class="px-6" clickHandler={() => null}>Filtruj</Button>
        </div>
    </div>
{/await}
