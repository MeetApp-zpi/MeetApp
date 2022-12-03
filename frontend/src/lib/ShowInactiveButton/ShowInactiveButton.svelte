<script lang="ts">
    import { goto } from '@roxi/routify';

    import MdNotificationsOff from 'svelte-icons/md/MdNotificationsOff.svelte';

    const navigateToInactive = () => {
        $goto(`/eventsInactive`);
    };
</script>

<button class="absolute rounded-full bg-grass bottom-36 right-4 h-12 w-12" on:click={navigateToInactive}>
    <div class="h-8 w-8 ml-auto mr-auto text-cocoa">
        <MdNotificationsOff />
    </div>
</button>
