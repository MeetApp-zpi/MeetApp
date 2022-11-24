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
        class="lg:hidden flex flex-col justify-center p-2 text-xs rounded-full
            {pageType === 'events' ? 'text-ivory' : ''}"
        href={$url('/events')}
        target="_top"
    >
        <div class="h-full">
            <FaCalendarDay />
        </div>
        <div class="text-center">Wydarzenia</div>
    </a>
    <a
        class="lg:hidden flex flex-col justify-center p-2 text-xs rounded-full
            {pageType === 'announcements' ? 'text-ivory' : ''}"
        href={$url('/announcements')}
        target="_top"
    >
        <div class="h-full">
            <MdAnnouncement />
        </div>
        <div class="text-center">Ogłoszenia</div>
    </a>
    <a
        class="lg:hidden flex flex-col justify-center p-2 text-xs rounded-full
            {pageType === 'meetings' ? 'text-ivory' : ''}"
        href={$url('/meetings')}
        target="_top"
    >
        <div class="h-full">
            <MeetingSymbol />
        </div>
        <div class="text-center">Spotkania</div>
    </a>
</footer>
