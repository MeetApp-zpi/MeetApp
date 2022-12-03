<script lang="ts">
    import { goto } from '@roxi/routify';

    import MdNotificationsActive from 'svelte-icons/md/MdNotificationsActive.svelte';

    const navigateToActive = () => {
        $goto(`/events`);
    };
</script>

<button class="absolute rounded-full bg-grass bottom-36 right-4 h-12 w-12" on:click={navigateToActive}>
    <div class="h-8 w-8 ml-auto mr-auto text-cocoa">
        <MdNotificationsActive />
    </div>
</button>
