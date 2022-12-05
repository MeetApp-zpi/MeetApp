<script lang="ts">
    import { redirect, url, goto, isActive } from '@roxi/routify';

    import FaCalendarDay from 'svelte-icons/fa/FaCalendarDay.svelte';
    import FaCommentAlt from 'svelte-icons/fa/FaCommentAlt.svelte';
    import FaHandsHelping from 'svelte-icons/fa/FaHandsHelping.svelte';
    import FaSignOutAlt from 'svelte-icons/fa/FaSignOutAlt.svelte';
    import MdAnnouncement from 'svelte-icons/md/MdAnnouncement.svelte';
    import MdMessage from 'svelte-icons/md/MdMessage.svelte';
    import MdPerson from 'svelte-icons/md/MdPerson.svelte';

    import { horizontalSlide } from './horizontalSlide';
    import { userDetails } from '../stores';
    import MeetingSymbol from '../../assets/MeetingSymbol.svelte';
    import execute from '../fetchWrapper';

    const logout = () => {
        execute('logout', 'POST').then((_) => (window.location.href = 'http://localhost:5173'));
    };

    const myActivities = () => {
        $isActive('/myActivities') ? (window.location.href = window.location.href) : $goto('/myActivities');
    };
</script>

<div class="z-20 h-screen flex flex-col w-2/3 fixed top-0 left-0" transition:horizontalSlide>
    {#if $userDetails !== null}
        <div class="bg-grass text-ivory flex h-32 items-end">
            <a href={$url('/profile')} class="flex flex-row items-end">
                <div class="">
                    <img
                        class="rounded-full mx-2 mb-2 h-12 w-12"
                        src={$userDetails.profilePicture}
                        alt="Profile avatar"
                        referrerpolicy="no-referrer"
                    />
                </div>
                <div class="flex flex-col mb-2">
                    <div class="text-ivory font-bold">{$userDetails.firstName} {$userDetails.lastName}</div>
                </div>
            </a>
        </div>
    {:else}
        <div class="bg-grass text-ivory h-32 flex items-end">
            <div class="text-ivory mb-2 ml-4 text-lg font-bold" on:click={() => $goto('/login')} on:keydown={() => $goto('/login')}>Zaloguj się</div>
        </div>
    {/if}
    <div class="h-full bg-ivory text-pine p-4">
        <div class="flex flex-row mb-2 items-center" on:click={() => $goto('/events')} on:keydown={() => $goto('/events')}>
            <div class="h-8 w-8 mr-2 text-taupe">
                <FaCalendarDay />
            </div>
            <div class="text-taupe">Wydarzenia</div>
        </div>
        <div class="flex flex-row mb-2 items-center" on:click={() => $goto('/announcements')} on:keydown={() => $goto('/announcements')}>
            <div class="h-8 w-8 mr-2 text-taupe">
                <MdAnnouncement />
            </div>
            <div class="text-taupe">Ogłoszenia</div>
        </div>
        <div class="flex flex-row mb-2 items-center" on:click={() => $goto('/meetings')} on:keydown={() => $goto('/meetings')}>
            <div class="h-8 w-8 mr-2 text-taupe">
                <MeetingSymbol />
            </div>
            <div class="text-taupe">Spotkania</div>
        </div>
        {#if $userDetails !== null}
            <div class="h-px w-[90%] my-2 ml-auto mr-auto bg-black bg-opacity-10" />
            <div class="flex flex-row mb-2 items-center" on:click={() => $redirect('/profile')} on:keydown={() => $redirect('/profile')}>
                <div class="h-8 w-8 mr-2 text-taupe">
                    <MdPerson />
                </div>
                <div class="text-taupe">Mój profil</div>
            </div>
            <div class="flex flex-row mb-2 items-center" on:click={() => $goto('/user/posts')} on:keydown={() => $goto('/user/posts')}>
                <div class="h-6 w-8 mr-2 text-taupe">
                    <FaCommentAlt />
                </div>
                <div class="text-taupe">Moje posty</div>
            </div>
            <div class="flex flex-row mb-2 items-center" on:click={myActivities} on:keydown={() => $goto('/myActivities')}>
                <div class="h-8 w-8 mr-2 text-taupe">
                    <FaHandsHelping />
                </div>
                <div class="text-taupe">Biorę udział</div>
            </div>
            <div class="flex flex-row mb-2 items-center" on:click={() => $goto('/chatrooms')} on:keydown={() => $goto('/chatrooms')}>
                <div class="h-8 w-8 mr-2 text-taupe">
                    <MdMessage />
                </div>
                <div class="text-taupe">Wiadomości</div>
            </div>
            <div class="flex flex-row items-center" on:click={logout} on:keydown={logout}>
                <div class="h-8 w-8 mr-2 text-taupe">
                    <FaSignOutAlt />
                </div>
                <div class="text-taupe">Wyloguj</div>
            </div>
        {/if}
    </div>
</div>
