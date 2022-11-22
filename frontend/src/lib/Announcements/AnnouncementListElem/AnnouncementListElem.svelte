<script lang="ts">
    import { slide } from 'svelte/transition';

    import FaMapMarkedAlt from 'svelte-icons/fa/FaMapMarkedAlt.svelte';
    import Button from '../../../lib/Button/Button.svelte';
    import execute from '../../../lib/fetchWrapper';

    export let data;
    export let areDetailsShown: boolean;
    export let clickHandler: () => void;

    let isEnrolled: boolean = false;

    const checkEnrolledStatus = () => {
        execute(`announcements/isEnrolled/${data.id}`, 'GET')
            .then((r) => r.json())
            .then((r) => (isEnrolled = r));
    };

    const enroll = () => {
        execute(`announcements/enroll/${data.id}`, 'GET').then((_) => checkEnrolledStatus());
    };

    const unenroll = () => {
        execute(`announcements/unenroll/${data.id}`, 'GET').then((_) => checkEnrolledStatus());
    };

    checkEnrolledStatus();
</script>

<div class="{isEnrolled ? 'bg-tusk' : 'bg-olive'} rounded-2xl m-2 p-2">
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
        </button>
        {#if areDetailsShown}
            <div transition:slide class="border-t-2 border-shadow">
                {data.description}
            </div>
            <div class="text-lg flex flex-row items-center" in:slide={{ delay: 100 }} out:slide>
                <div class="w-12 h-12 mr-2">
                    <img class="rounded-full" src={data.author.profilePicture} alt="Profile avatar" />
                </div>
                {data.author.firstName}
                {data.author.lastName}
            </div>
            <div class="self-center my-2" in:slide={{ delay: 100 }} out:slide>
                {#if isEnrolled}
                    <Button class="text-base px-10 py-1 mx-12 my-2" clickHandler={unenroll}>Wypisuję się!</Button>
                {:else}
                    <Button class="text-base px-10 py-1 mx-12 my-2" clickHandler={enroll}>Zapisuję się!</Button>
                {/if}
            </div>
        {/if}
    </div>
</div>

<style>
    .border-shadow {
        border-color: rgba(0, 0, 0, 0.1);
    }
</style>
