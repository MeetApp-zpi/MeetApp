<script lang="ts">
    import FaCalendarDay from 'svelte-icons/fa/FaCalendarDay.svelte';
    import MdAnnouncement from 'svelte-icons/md/MdAnnouncement.svelte';
    import MeetingSymbol from '../../assets/MeetingSymbol.svelte';
    import { url } from '@roxi/routify';

    import type { PageType } from './types';

    export let pageType: PageType;
</script>

<footer class="flex justify-between p-2 bg-grass h-16 lg:hidden">
    <a
        class="lg:hidden flex flex-col p-2 focus:ring-1 focus:ring-cocoa transition ease-in-out delay-100 text-xs rounded-full hover:bg-tea
            {pageType === 'events' ? 'text-ivory' : ''}"
        href={$url('/events')}
    >
        <FaCalendarDay />
        <div class="text-center">Wydarzenia</div>
    </a>
    <a
        class="lg:hidden flex flex-col justify-center p-2 focus:ring-1 focus:ring-cocoa transition ease-in-out delay-100 text-xs rounded-full hover:bg-tea 
            {pageType === 'announcements' ? 'text-ivory' : ''}"
        href={$url('/announcements')}
    >
        <div class="h-full">
            <MdAnnouncement />
        </div>
        <div class="text-center">Ogłoszenia</div>
    </a>
    <a
        class="lg:hidden flex flex-col p-2 focus:ring-1 focus:ring-cocoa transition ease-in-out delay-100 text-xs rounded-full hover:bg-tea 
            {pageType === 'meetings' ? 'text-ivory' : ''}"
        href={$url('/meetings')}
    >
        <MeetingSymbol />
        <div class="text-center">Spotkania</div>
    </a>
</footer>
