<script lang="ts">
    import { goto } from '@roxi/routify';

    import MdNotificationsOff from 'svelte-icons/md/MdNotificationsOff.svelte';

    let cssClass: string;
    export { cssClass as class };

    const navigateToInactive = () => {
        $goto(`/eventsInactive`);
    };
</script>

<button class="absolute rounded-full bg-grass right-4 h-12 w-12 {cssClass} lg:h-20 lg:w-20 lg:right-20 lg:bottom-48" on:click={navigateToInactive}>
    <div class="h-8 w-8 ml-auto mr-auto text-cocoa lg:w-12 lg:h-12">
        <MdNotificationsOff />
    </div>
</button>
