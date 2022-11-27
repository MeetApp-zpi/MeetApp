<script lang="ts">
    import { url } from '@roxi/routify';
    import MdNotificationsActive from 'svelte-icons/md/MdNotificationsActive.svelte';
    import MdNotificationsOff from 'svelte-icons/md/MdNotificationsOff.svelte';

    export let pageType: 'active' | 'inactive';
</script>

<footer class="flex flex-row justify-around p-2 bg-grass h-16 lg:hidden">
    <a
        class="lg:hidden flex flex-col justify-center p-2 text-xs rounded-full {pageType === 'active' ? 'text-ivory' : ''}"
        target="_top"
        href={$url('/user/posts')}
    >
        <div class="h-full">
            <MdNotificationsActive />
        </div>
        <div class="text-center">Aktywne</div>
    </a>
    <a
        class="lg:hidden flex flex-col justify-center p-2 text-xs rounded-full {pageType === 'inactive' ? 'text-ivory' : ''}"
        target="_top"
        href={$url('/user/postsInactive')}
    >
        <div class="h-full">
            <MdNotificationsOff />
        </div>
        <div class="text-center">Nieaktywne</div>
    </a>
</footer>
