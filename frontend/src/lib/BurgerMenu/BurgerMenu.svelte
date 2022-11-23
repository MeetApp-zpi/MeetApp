<script lang="ts">
    import { goto, isActive } from '@roxi/routify';

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
        <div class="bg-grass text-ivory flex flex-row h-32 items-end">
            <div class="">
                <img class="rounded-full mx-2 mb-2 h-12 w-12" src={$userDetails.profilePicture} alt="Profile avatar" referrerpolicy="no-referrer" />
            </div>
            <div class="flex flex-col mb-2">
                <div class="text-ivory font-bold">{$userDetails.firstName} {$userDetails.lastName}</div>
            </div>
        </div>
    {:else}
        <div class="bg-grass text-ivory h-32 flex items-end">
            <div class="text-ivory mb-2 ml-4 text-lg font-bold" on:click={() => $goto('/login')} on:keydown={() => $goto('/login')}>Zaloguj się</div>
        </div>
    {/if}
    <div class="h-full bg-ivory text-pine p-4">
        <div class="flex flex-row mb-2 items-center" on:click={() => $goto('/events')} on:keydown={() => $goto('/events')}>
            <div class="h-8 w-8 mr-2">
                <FaCalendarDay />
            </div>
            <div class="">Wydarzenia</div>
        </div>
        <div class="flex flex-row mb-2 items-center" on:click={() => $goto('/announcements')} on:keydown={() => $goto('/announcements')}>
            <div class="h-8 w-8 mr-2">
                <MdAnnouncement />
            </div>
            <div class="">Ogłoszenia</div>
        </div>
        <div class="flex flex-row mb-2 items-center" on:click={() => $goto('/meetings')} on:keydown={() => $goto('/meetings')}>
            <div class="h-8 w-8 mr-2">
                <MeetingSymbol />
            </div>
            <div class="">Spotkania</div>
        </div>
        {#if $userDetails !== null}
            <div class="h-px w-[90%] my-2 ml-auto mr-auto bg-black bg-opacity-10" />
            <div class="flex flex-row mb-2 items-center">
                <div class="h-8 w-8 mr-2">
                    <MdPerson />
                </div>
                <div class="">Mój profil</div>
            </div>
            <div class="flex flex-row mb-2 items-center" on:click={() => $goto('/user/posts')} on:keydown={() => $goto('/user/posts')}>
                <div class="h-6 w-8 mr-2">
                    <FaCommentAlt />
                </div>
                <div class="">Moje posty</div>
            </div>
            <div class="flex flex-row mb-2 items-center" on:click={myActivities} on:keydown={() => $goto('/myActivities')}>
                <div class="h-8 w-8 mr-2">
                    <FaHandsHelping />
                </div>
                <div class="">Biorę udział</div>
            </div>
            <div class="flex flex-row mb-2 items-center">
                <div class="h-8 w-8 mr-2">
                    <MdMessage />
                </div>
                <div class="">Wiadomości</div>
            </div>
            <div class="flex flex-row items-center" on:click={logout} on:keydown={logout}>
                <div class="h-8 w-8 mr-2">
                    <FaSignOutAlt />
                </div>
                <div class="">Wyloguj</div>
            </div>
        {/if}
    </div>
</div>
