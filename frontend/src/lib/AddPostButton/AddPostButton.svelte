<script lang="ts">
    import { redirect } from '@roxi/routify';

    import MdAdd from 'svelte-icons/md/MdAdd.svelte';

    export let pageType: 'announcements' | 'events' | 'meetings';

    const navigateToCreator = () => {
        $redirect(`/${pageType}/create`);
    };
</script>

<button class="absolute rounded-full bg-grass bottom-20 right-4 h-12 w-12" on:click={navigateToCreator}>
    <div class="h-8 w-8 ml-auto mr-auto">
        <MdAdd />
    </div>
</button>
