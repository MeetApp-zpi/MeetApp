<script lang="ts">
    import MdMenu from 'svelte-icons/md/MdMenu.svelte';
</script>

<header
    class="flex justify-between header-background-color max-h-10 p-2 lg:max-h-20 lg:h-20 lg:p-4"
>
    <div
        class="lg:hidden w-10 hover:cursor-pointer rounded-full hover:bg-[#d0d9ba] transition-colors ease-in-out delay-75
        active:ring-1 active:ring-[#38302E]"
    >
        <MdMenu />
    </div>
    <div class="w-16 justify-self-center h-full lg:w-32">
        <img alt="MeetApp logo" src="/logo.png" width="360" height="151" class="h-full" />
    </div>
    <div class="w-10" />
</header>

<style>
    .header-background-color {
        background-color: var(--grass);
    }
</style>
