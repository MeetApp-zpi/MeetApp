<script lang="ts">
    import { fade } from 'svelte/transition';
    import { goto, redirect, isActive } from '@roxi/routify';

    import FaUserCircle from 'svelte-icons/fa/FaUserCircle.svelte';
    import FaSignOutAlt from 'svelte-icons/fa/FaSignOutAlt.svelte';
    import MdMessage from 'svelte-icons/md/MdMessage.svelte';
    import MdPerson from 'svelte-icons/md/MdPerson.svelte';
    import MdMenu from 'svelte-icons/md/MdMenu.svelte';

    import BurgerMenu from '../BurgerMenu/BurgerMenu.svelte';
    import execute from '../fetchWrapper';
    import { userDetails, haveUnreadMessage } from '../stores';
    import type { PageType } from '../Footer/types';

    export let pageType: PageType = 'main';

    let isBurgerOpen = false;

    const switchBurger = () => {
        isBurgerOpen = !isBurgerOpen;
    };

    const disableBurger = () => {
        isBurgerOpen = false;
    };

    const logout = () => {
        execute('logout', 'POST').then((_) => (window.location.href = 'http://localhost:5173'));
    };

    let showDropdown = false;
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
    <div class="justify-between lg:justify-between lg:flex lg:flex-row h-full lg:w-1/5">
        <img alt="MeetApp logo" src="/logo.png" class="h-full hover:cursor-pointer" on:click={() => $goto('/')} on:keydown={() => $goto('/')} />
    </div>

    <div class="hidden lg:block lg:flex lg:flex-row lg:w-full lg:justify-center lg:items-center">
        <div
            class="hidden lg:block group hover:cursor-pointer text-taupe px-4 text-xl"
            on:click={() => (window.location.href = 'http://localhost:5173/announcements')}
            on:keydown={() => (window.location.href = 'http://localhost:5173/announcements')}
        >
            <span class={pageType === 'announcements' ? 'text-ivory' : 'text-cocoa'}> Ogłoszenia </span>
        </div>
        <div
            class="hidden lg:block group hover:cursor-pointer text-taupe px-4 text-xl"
            on:click={() => (window.location.href = 'http://localhost:5173/meetings')}
            on:keydown={() => (window.location.href = 'http://localhost:5173/meetings')}
        >
            <span class={pageType === 'meetings' ? 'text-ivory' : 'text-cocoa'}>Spotkania</span>
        </div>
        <div
            class="hidden lg:block group hover:cursor-pointer text-taupe px-4 text-xl"
            on:click={() => (window.location.href = 'http://localhost:5173/events')}
            on:keydown={() => (window.location.href = 'http://localhost:5173/events')}
        >
            <span class={pageType === 'events' ? 'text-ivory' : 'text-cocoa'}>Wydarzenia</span>
        </div>
    </div>
    <div class="block lg:hidden w-12" />
    <div class="hidden lg:block lg:relative lg:flex lg:flex-row lg:justify-end lg:w-1/5">
        {#if $userDetails !== null}
            <img
                class="rounded-full mx-2 mb-2 h-12 w-12 hover:cursor-pointer"
                src={$userDetails.profilePicture}
                alt="Profile avatar"
                referrerpolicy="no-referrer"
                on:click={() => (showDropdown = !showDropdown)}
            />
            {#if showDropdown}
                <div class="absolute right-0 mt-14 py-2 bg-[#e5e7eb] rounded-md shadow-xl z-10">
                    <div class="px-4 py-2">
                        <div
                            class="group hover:cursor-pointer text-taupe flex flex-row items-center"
                            on:click={() => $goto('/profile')}
                            on:keydown={() => $goto('/profile')}
                        >
                            <div class="h-8 w-8">
                                <MdPerson />
                            </div>
                            <span class="pl-2">Mój Profil</span>
                        </div>
                        <div
                            class="group hover:cursor-pointer text-taupe flex flex-row items-center relative"
                            on:click={() => $goto('/chatrooms')}
                            on:keydown={() => $goto('/chatrooms')}
                        >
                            <div class="h-8 w-8">
                                <MdMessage />
                            </div>
                            {#if $haveUnreadMessage}
                                <div class="absolute h-4 w-4 bg-orange rounded-full left-5 -top-1" />
                            {/if}
                            <span class="pl-2">Wiadomości</span>
                        </div>
                        <hr class="mx-auto my-4" />
                        <div class="group hover:cursor-pointer text-taupe flex flex-row items-center" on:click={logout} on:keydown={logout}>
                            <div class="h-8 w-8">
                                <FaSignOutAlt />
                            </div>
                            <span class="pl-2">Wyloguj</span>
                        </div>
                    </div>
                </div>
            {/if}
        {:else}
            <div class="hover:cursor-pointer text-taupe" on:click={() => $redirect('/login')} on:keydown={() => $redirect('/login')}>
                <FaUserCircle />
            </div>
        {/if}
    </div>
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
