<script lang="ts">
    import MdMenu from 'svelte-icons/md/MdMenu.svelte';
</script>

<header class="flex justify-between bg-grass p-2 h-16 align-middle">
    <div
        class="lg:hidden p-1 hover:cursor-pointer rounded-full hover:bg-tea transition-colors ease-in-out delay-75
        active:ring-1 active:ring-cocoa"
    >
        <MdMenu />
    </div>
    <div class="justify-self-center h-full lg:w-52">
        <img alt="MeetApp logo" src="/logo.png" class="h-full" />
    </div>
    <div class="w-10" />
</header>
