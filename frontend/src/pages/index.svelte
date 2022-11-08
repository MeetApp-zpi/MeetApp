<script lang="ts">
    import FaCalendarDay from 'svelte-icons/fa/FaCalendarDay.svelte';
    import MdAnnouncement from 'svelte-icons/md/MdAnnouncement.svelte';
    import MeetingSymbol from '../assets/MeetingSymbol.svelte';
    import { url } from '@roxi/routify';

    import Footer from '../lib/Footer/Footer.svelte';
    import Header from '../lib/Header/Header.svelte';
    import Button from '../lib/Button/Button.svelte';
</script>

<div class="h-screen">
    <Header />
    <div class="flex flex-col h-[90%] overflow-auto">
        <a
            href={$url('/events')}
            class="p-8 mx-10 my-4 h-1/3 text-pine border-2 rounded-2xl flex flex-col items-center justify-center
            hover:bg-pine hover:text-ivory transition ease-in-out delay-100"
        >
            <div class="w-10">
                <FaCalendarDay />
            </div>
            <div class="text-2xl">Wydarzenia</div>
        </a>
        <a
            href={$url('/announcements')}
            class="p-8 mx-10 my-4 h-1/3 text-grass border-2 rounded-2xl flex flex-col text-center items-center justify-center
            hover:bg-grass hover:text-ivory transition ease-in-out delay-100"
        >
            <div class="w-10">
                <MdAnnouncement />
            </div>
            <div class="text-2xl">Ogłoszenia</div>
        </a>
        <a
            href={$url('/meetings')}
            class="p-8 mx-10 my-4 h-1/3 text-pickle border-2 rounded-2xl flex flex-col text-center items-center justify-center
            hover:bg-pickle hover:text-ivory transition ease-in-out delay-100"
        >
            <div class="w-10">
                <MeetingSymbol />
            </div>
            <div class="text-2xl">Spotkania</div>
        </a>
    </div>
</div>
