<script lang="ts">
    import FaMapMarkedAlt from 'svelte-icons/fa/FaMapMarkedAlt.svelte';
    import Button from '../../../lib/Button/Button.svelte';

    export let data;
    export let areDetailsShown: boolean;
</script>

<div class="bg-olive rounded-2xl m-2 p-2 hover:cursor-pointer">
    <div class="flex flex-col">
        <div class="font-bold">
            {data.title}
        </div>
        <div class="flex flex-row">
            <div class="w-6 mx-2">
                <FaMapMarkedAlt />
            </div>
            {data.location.city.name}
        </div>
        {#if areDetailsShown}
            <div>
                {data.description}
            </div>
            <div class="text-lg">
                {data.author.firstName}
                {data.author.lastName}
            </div>
            <div class="self-center my-2">
                <Button marginX={12} marginY={2} textSize={20} clickHandler={() => null}>Zapisuję się!</Button>
            </div>
        {/if}
    </div>
</div>
