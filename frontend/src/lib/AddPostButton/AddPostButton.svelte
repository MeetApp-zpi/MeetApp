<script lang="ts">
    import { goto } from '@roxi/routify';

    import MdAdd from 'svelte-icons/md/MdAdd.svelte';

    export let pageType: 'announcements' | 'events' | 'meetings';

    const navigateToCreator = () => {
        $goto(`/${pageType}/create`);
    };
</script>

<button class="absolute rounded-full bg-grass bottom-20 right-4 h-12 w-12 lg:h-20 lg:w-20 lg:right-20" on:click={navigateToCreator}>
    <div class="h-8 w-8 ml-auto mr-auto lg:w-12 lg:h-12 text-cocoa">
        <MdAdd />
    </div>
</button>
