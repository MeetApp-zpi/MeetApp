<script lang="ts">
    import { goto } from '@roxi/routify';
    import execute from '../../lib/fetchWrapper';
    import { userDetails } from '../../lib/stores';

    import Header from '../../lib/Header/Header.svelte';
    import UserChatPill from '../../lib/UserChatPill/UserChatPill.svelte';

    let chatrooms = [];

    let promise = execute('chatrooms/forClient', 'GET')
        .then((r) => (r.status === 500 ? $goto('/') : r.json()))
        .then((r) => (chatrooms = r));
</script>

<div class="h-screen">
    <Header />
    {#await promise then _}
        {#each chatrooms as chatroom}
            {#if $userDetails.email !== chatroom.firstClient.email}
                <UserChatPill data={chatroom.firstClient} chatroomId={chatroom.id} />
            {:else}
                <UserChatPill data={chatroom.secondClient} chatroomId={chatroom.id} />
            {/if}
        {/each}
    {/await}
</div>
