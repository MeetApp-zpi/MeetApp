<script lang="ts">
    import { redirect } from '@roxi/routify';

    import MdAdd from 'svelte-icons/md/MdAdd.svelte';

    export let pageType: 'announcements' | 'events' | 'meetings';

    const navigateToCreator = () => {
        $redirect(`/${pageType}/create`);
    };
</script>

<button class="absolute rounded-full bg-grass border-pine border-2 bottom-20 right-2 h-16 w-16" on:click={navigateToCreator}>
    <MdAdd />
</button>
