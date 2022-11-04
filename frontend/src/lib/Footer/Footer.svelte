<script lang="ts">
    import FaCalendarDay from 'svelte-icons/fa/FaCalendarDay.svelte';
    import MdAnnouncement from 'svelte-icons/md/MdAnnouncement.svelte';
    import MeetingSymbol from '../../assets/MeetingSymbol.svelte';
    import { url } from '@roxi/routify';

    import type { PageType } from './types';

    export let pageType: PageType;
</script>

<footer class="flex justify-between p-2 footer-background-color h-16 max-h-16 lg:h-20 lg:p-4">
    <a
        class="lg:hidden flex flex-col p-2 focus:ring-1 focus:ring-[#38302E] transition ease-in-out delay-100 text-xs rounded-full hover:bg-[#d0d9ba] 
            {pageType === 'events' ? 'text-white' : ''}"
        href={$url('/events')}
    >
        <FaCalendarDay />
        <div class="text-center">Wydarzenia</div>
    </a>
    <a
        class="lg:hidden flex flex-col p-2 focus:ring-1 focus:ring-[#38302E] transition ease-in-out delay-100 text-xs rounded-full hover:bg-[#d0d9ba] 
            {pageType === 'announcements' ? 'text-white' : ''}"
        href={$url('/announcements')}
    >
        <MdAnnouncement />
        <div class="text-center">Ogłoszenia</div>
    </a>
    <a
        class="lg:hidden flex flex-col p-2 focus:ring-1 focus:ring-[#38302E] transition ease-in-out delay-100 text-xs rounded-full hover:bg-[#d0d9ba] 
            {pageType === 'meetings' ? 'text-white' : ''}"
        href={$url('/meetings')}
    >
        <MeetingSymbol />
        <div class="text-center">Spotkania</div>
    </a>
</footer>

<style>
    .footer-background-color {
        background-color: var(--grass);
    }
</style>
