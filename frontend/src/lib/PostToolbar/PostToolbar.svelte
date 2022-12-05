<script lang="ts">
    import { goto } from '@roxi/routify';

    import FaUserCheck from 'svelte-icons/fa/FaUserCheck.svelte';
    import FaUserFriends from 'svelte-icons/fa/FaUserFriends.svelte';
    import FaUserSlash from 'svelte-icons/fa/FaUserSlash.svelte';
    import MdDelete from 'svelte-icons/md/MdDelete.svelte';
    import MdEdit from 'svelte-icons/md/MdEdit.svelte';

    import execute from '../fetchWrapper';

    export let postId: number;
    export let postType: 'events' | 'meetings' | 'announcements';
    export let isPostActive: boolean;
    export let updatePosts: (newPosts: any) => void;

    const getUpdatedPosts = () => {
        return execute(`users/posts${isPostActive ? '' : 'Inactive'}?page=0`, 'GET').then((r) => r.json());
    };

    const deletePost = () => {
        execute(`${postType}/${postId}`, 'DELETE').then((_) => updatePosts(getUpdatedPosts()));
    };

    const activate = () => {
        execute(`posts/activate/${postId}`, 'GET').then((_) => updatePosts(getUpdatedPosts()));
    };

    const deactivate = () => {
        execute(`posts/deactivate/${postId}`, 'GET').then((_) => updatePosts(getUpdatedPosts()));
    };
</script>

<div class="flex flex-row -mt-4 justify-end mr-4">
    <div
        class="bg-olive w-10 h-10 rounded-b-lg p-2 text-cocoa hover:cursor-pointer"
        on:click={() => $goto(`/enrolled/${postId}`)}
        on:keydown={() => $goto(`/enrolled/${postId}`)}
    >
        <FaUserFriends />
    </div>
    <div
        class="bg-olive mx-4 w-10 h-10 rounded-b-lg p-2 text-cocoa hover:cursor-pointer"
        on:click={() => $goto(`/${postType}/edit/${postId}`)}
        on:keydown={() => $goto(`/${postType}/edit/${postId}`)}
    >
        <MdEdit />
    </div>
    {#if isPostActive}
        <div class="bg-olive mr-4 w-10 h-10 rounded-b-lg p-2 text-cocoa hover:cursor-pointer" on:click={deactivate} on:keydown={deactivate}>
            <FaUserSlash />
        </div>
    {:else}
        <div class="bg-olive mr-4 w-10 h-10 rounded-b-lg p-2 text-cocoa hover:cursor-pointer" on:click={activate} on:keydown={activate}>
            <FaUserCheck />
        </div>
    {/if}
    <div class="bg-olive w-10 h-10 rounded-b-lg p-2 text-cocoa hover:cursor-pointer" on:click={deletePost} on:keydown={deletePost}>
        <MdDelete />
    </div>
</div>
