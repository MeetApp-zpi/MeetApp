<script lang="ts">
    import { redirect } from '@roxi/routify';
    import execute from '../../lib/fetchWrapper';
    import { userDetails } from '../../lib/stores';

    import Header from '../../lib/Header/Header.svelte';
    import UserChatPill from '../../lib/UserChatPill/UserChatPill.svelte';

    let chatrooms = [];

    let promise = execute('chatrooms/forClient', 'GET')
        .then((r) => (r.status === 500 ? $redirect('/login') : r.json()))
        .then((r) => (chatrooms = r));
</script>

<div class="h-screen">
    <Header />
    <div class="lg:w-1/3 lg:mx-auto">
        {#await promise then _}
            {#each chatrooms as chatroom}
                <UserChatPill partner={chatroom.chatPartner} chatroomId={chatroom.id} noUnreadMessage={chatroom.hasUnreadMessage} />
            {/each}
        {/await}
    </div>
</div>
