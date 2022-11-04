<script lang="ts">
    import FaCalendarDay from 'svelte-icons/fa/FaCalendarDay.svelte';
    import MdAnnouncement from 'svelte-icons/md/MdAnnouncement.svelte';
    import MdAccessibility from 'svelte-icons/md/MdAccessibility.svelte';
    import type { PageType } from './types';

    export let pageType: PageType;
</script>

<footer class="flex justify-between p-2 footer-background-color h-16 max-h-16 lg:h-20 lg:p-4">
    <div class="lg:hidden flex flex-col {pageType === 'events' ? 'text-white' : ''}">
        <FaCalendarDay />
        <div class="text-center">Wydarzenia</div>
    </div>
    <div class="lg:hidden flex flex-col {pageType === 'announcements' ? 'text-white' : ''}">
        <MdAnnouncement />
        <div class="text-center">Ogłoszenia</div>
    </div>
    <div class="lg:hidden flex flex-col {pageType === 'meetings' ? 'text-white' : ''}">
        <MdAccessibility />
        <div class="text-center">Spotkania</div>
    </div>
</footer>

<style>
    .footer-background-color {
        background-color: var(--grass);
    }
</style>
