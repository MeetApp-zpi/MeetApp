<script lang="ts">
    import { fade } from 'svelte/transition';
    import MdMenu from 'svelte-icons/md/MdMenu.svelte';

    import BurgerMenu from '../BurgerMenu/BurgerMenu.svelte';

    let isBurgerOpen: boolean = false;

    const switchBurger = () => {
        isBurgerOpen = !isBurgerOpen;
    };

    const disableBurger = () => {
        isBurgerOpen = false;
    };
</script>

<header class="flex justify-between bg-grass p-2 h-16 align-middle">
    <div
        class="lg:hidden p-1 hover:cursor-pointer rounded-full hover:bg-tea transition-colors ease-in-out delay-75
        active:ring-1 active:ring-cocoa"
        on:click={switchBurger}
        on:keydown={switchBurger}
    >
        <MdMenu />
    </div>
    <div class="justify-self-center h-full lg:w-52">
        <img alt="MeetApp logo" src="/logo.png" class="h-full" />
    </div>
    <div class="w-10" />
</header>
{#if isBurgerOpen}
    <BurgerMenu />
    <div
        on:click={disableBurger}
        on:keydown={disableBurger}
        class="w-screen h-screen z-10 fixed bg-black bg-opacity-30 top-0 left-0"
        transition:fade
    />
{/if}
