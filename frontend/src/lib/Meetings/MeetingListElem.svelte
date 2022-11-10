<script lang="ts">
    import { slide } from 'svelte/transition';

    import FaCalendarAlt from 'svelte-icons/fa/FaCalendarAlt.svelte';
    import FaMapMarkedAlt from 'svelte-icons/fa/FaMapMarkedAlt.svelte';
    import MdPeople from 'svelte-icons/md/MdPeople.svelte';
    import Button from '../../lib/Button/Button.svelte';

    export let data;
    export let areDetailsShown: boolean;
    export let clickHandler: () => void;
</script>

<div class="bg-olive rounded-2xl m-2 p-2">
    <div class="flex flex-col">
        <button on:click={clickHandler} class="hover:cursor-pointer">
            <div class="font-bold text-left">
                {data.title}
            </div>
            <div class="flex flex-row">
                <div class="w-6 mx-2">
                    <FaMapMarkedAlt />
                </div>
                {data.location.city.name}
            </div>
            <div class="flex flex-row">
                <div class="w-6 mx-2">
                    <FaCalendarAlt />
                </div>
                {data.meetingDate}
            </div>
            <div class="flex flex-row">
                <div class="w-6 mx-2">
                    <MdPeople />
                </div>
                <div class="text-orange">
                    {data.enrolled} &nbsp;
                </div>
                {data.personQuota !== null ? '/ ' + data.personQuota : ''}
            </div>
        </button>
        {#if areDetailsShown}
            <div transition:slide class="border-t-2 border-shadow">
                {data.description}
            </div>
            <div class="text-lg flex flex-row items-center" in:slide={{ delay: 100 }} out:slide>
                <div class="w-12 mr-2">
                    <img class="rounded-full" src={data.author.profilePicture} alt="Profile avatar" />
                </div>
                {data.author.firstName}
                {data.author.lastName}
            </div>
            <div class="self-center my-2" in:slide={{ delay: 100 }} out:slide>
                <Button class="text-base px-12 py-1 mx-12 my-2" clickHandler={() => null}>Zapisuję się!</Button>
            </div>
        {/if}
    </div>
</div>

<style>
    .border-shadow {
        border-color: rgba(0, 0, 0, 0.1);
    }
</style>
