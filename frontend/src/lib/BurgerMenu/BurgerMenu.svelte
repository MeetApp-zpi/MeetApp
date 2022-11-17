<script lang="ts">
    import FaCalendarDay from 'svelte-icons/fa/FaCalendarDay.svelte';
    import MdAnnouncement from 'svelte-icons/md/MdAnnouncement.svelte';
    import MdPerson from 'svelte-icons/md/MdPerson.svelte';
    import MdSettings from 'svelte-icons/md/MdSettings.svelte';

    import { horizontalSlide } from './horizontalSlide';
    import MeetingSymbol from '../../assets/MeetingSymbol.svelte';
</script>

<div class="z-20 h-screen flex flex-col fixed top-0 left-0" transition:horizontalSlide>
    <div class="bg-grass text-ivory flex flex-row h-32 items-end">
        <div class="">ikonka</div>
        <div class="flex flex-col">
            <div class="">JD ORKA</div>
            <div class="">alegierczyca@gmail.com</div>
        </div>
    </div>
    <div class="h-full bg-ivory text-pine p-4">
        <div class="flex flex-row mb-2 items-center">
            <div class="h-8 w-8 mr-2">
                <FaCalendarDay />
            </div>
            <div class="">Wydarzenia</div>
        </div>
        <div class="flex flex-row mb-2 items-center">
            <div class="h-8 w-8 mr-2">
                <MdAnnouncement />
            </div>
            <div class="">Ogłoszenia</div>
        </div>
        <div class="flex flex-row mb-2 items-center">
            <div class="h-8 w-8 mr-2">
                <MeetingSymbol />
            </div>
            <div class="">Spotkania</div>
        </div>
        <div class="h-px w-[90%] my-2 ml-auto mr-auto bg-black bg-opacity-10" />
        <div class="flex flex-row mb-2 items-center">
            <div class="h-8 w-8 mr-2">
                <MdPerson />
            </div>
            <div class="">Mój profil</div>
        </div>
        <div class="flex flex-row items-center">
            <div class="h-8 w-8 mr-2">
                <MdSettings />
            </div>
            <div class="">Ustawienia</div>
        </div>
    </div>
</div>
