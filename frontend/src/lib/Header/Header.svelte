<script lang="ts">
    import { fade } from 'svelte/transition';
    import { goto, redirect, isActive } from '@roxi/routify';

    import FaCalendarDay from 'svelte-icons/fa/FaCalendarDay.svelte';
    import FaCommentAlt from 'svelte-icons/fa/FaCommentAlt.svelte';
    import FaHandsHelping from 'svelte-icons/fa/FaHandsHelping.svelte';
    import FaUserCircle from 'svelte-icons/fa/FaUserCircle.svelte';
    import FaSignOutAlt from 'svelte-icons/fa/FaSignOutAlt.svelte';
    import MdAnnouncement from 'svelte-icons/md/MdAnnouncement.svelte';
    import MdMessage from 'svelte-icons/md/MdMessage.svelte';
    import MdPerson from 'svelte-icons/md/MdPerson.svelte';
    import MdMenu from 'svelte-icons/md/MdMenu.svelte';
    import MeetingSymbol from '../../assets/MeetingSymbol.svelte';

    import BurgerMenu from '../BurgerMenu/BurgerMenu.svelte';
    import HeaderTooltip from '../HeaderTooltip/HeaderTooltip.svelte';
    import execute from '../fetchWrapper';
    import { userDetails } from '../stores';

    let isBurgerOpen: boolean = false;

    const switchBurger = () => {
        isBurgerOpen = !isBurgerOpen;
    };

    const disableBurger = () => {
        isBurgerOpen = false;
    };

    const logout = () => {
        execute('logout', 'POST').then((_) => (window.location.href = 'http://localhost:5173'));
    };

    const myActivities = () => {
        $isActive('/myActivities') ? (window.location.href = window.location.href) : $goto('/myActivities');
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
    <div class="justify-between lg:w-1/4 lg:justify-between lg:flex lg:flex-row h-full">
        <img alt="MeetApp logo" src="/logo.png" class="h-full hover:cursor-pointer" on:click={() => $goto('/')} on:keydown={() => $goto('/')} />
        <div
            class="hidden lg:block group hover:cursor-pointer text-taupe"
            on:click={() => $goto('/announcements')}
            on:keydown={() => $goto('/announcements')}
        >
            <MdAnnouncement />
            <HeaderTooltip tooltipText="Ogłoszenia" />
        </div>
        <div class="hidden lg:block group hover:cursor-pointer text-taupe" on:click={() => $goto('/meetings')} on:keydown={() => $goto('/meetings')}>
            <MeetingSymbol />
            <HeaderTooltip tooltipText="Spotkania" />
        </div>
        <div class="hidden lg:block group hover:cursor-pointer text-taupe" on:click={() => $goto('/events')} on:keydown={() => $goto('/events')}>
            <FaCalendarDay />
            <HeaderTooltip tooltipText="Wydarzenia" />
        </div>
    </div>
    <div class="block lg:hidden w-12" />
    <div class="hidden lg:flex lg:flex-row lg:w-1/3 {$userDetails !== null ? 'lg:justify-between' : 'lg:justify-end'}">
        {#if $userDetails !== null}
            <div class="group hover:cursor-pointer text-taupe" on:click={() => $redirect('/profile')} on:keydown={() => $redirect('/profile')}>
                <MdPerson />
                <HeaderTooltip tooltipText="Profil" />
            </div>
            <div class="group hover:cursor-pointer text-taupe" on:click={() => $goto('/user/posts')} on:keydown={() => $goto('/user/posts')}>
                <FaCommentAlt />
                <HeaderTooltip tooltipText="Moje posty" />
            </div>
            <div class="group hover:cursor-pointer text-taupe" on:click={myActivities} on:keydown={() => $goto('/myActivities')}>
                <FaHandsHelping />
                <HeaderTooltip tooltipText="Biorę udział" />
            </div>
            <div class="group hover:cursor-pointer text-taupe" on:click={() => $goto('/chatrooms')} on:keydown={() => $goto('/chatrooms')}>
                <MdMessage />
                <HeaderTooltip tooltipText="Wiadomości" />
            </div>
            <div class="group hover:cursor-pointer text-taupe" on:click={logout} on:keydown={logout}>
                <FaSignOutAlt />
                <HeaderTooltip tooltipText="Wyloguj" />
            </div>
            <img
                class="rounded-full mx-2 mb-2 h-12 w-12 hover:cursor-pointer"
                src={$userDetails.profilePicture}
                alt="Profile avatar"
                referrerpolicy="no-referrer"
                on:click={() => $goto('/profile')}
                on:keydown={() => $goto('/profile')}
            />
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
