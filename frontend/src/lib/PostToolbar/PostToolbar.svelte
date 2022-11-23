<script lang="ts">
    import FaUserFriends from 'svelte-icons/fa/FaUserFriends.svelte';
    import FaUserSlash from 'svelte-icons/fa/FaUserSlash.svelte';
    import MdDelete from 'svelte-icons/md/MdDelete.svelte';
    import MdEdit from 'svelte-icons/md/MdEdit.svelte';

    import { goto } from '@roxi/routify';

    export let postId: number;
    export let postType: 'events' | 'meetings' | 'announcements';
</script>

<div class="flex flex-row -mt-4 justify-end mr-4">
    <div class="bg-olive w-10 h-10 rounded-b-lg p-2" on:click={() => $goto(`/enrolled/${postId}`)} on:keydown={() => $goto(`/enrolled/${postId}`)}>
        <FaUserFriends />
    </div>
    <div
        class="bg-olive mx-4 w-10 h-10 rounded-b-lg p-2"
        on:click={() => $goto(`/${postType}/edit/${postId}`)}
        on:keydown={() => $goto(`/${postType}/edit/${postId}`)}
    >
        <MdEdit />
    </div>
    <div class="bg-olive mr-4 w-10 h-10 rounded-b-lg p-2">
        <FaUserSlash />
    </div>
    <div class="bg-olive w-10 h-10 rounded-b-lg p-2">
        <MdDelete />
    </div>
</div>
