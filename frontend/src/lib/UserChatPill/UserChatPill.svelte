<script lang="ts">
    import { goto } from '@roxi/routify';

    export let partner;
    export let chatroomId: number;
    export let noUnreadMessage: boolean;
</script>

<div
    class="rounded-2xl m-2 p-2 justify-around flex flex-row bg-olive text-cocoa items-center hover:cursor-pointer"
    on:click={$goto(`/chat/${chatroomId}`)}
    on:keydown={$goto(`/chat/${chatroomId}`)}
>
    {#if !noUnreadMessage}
        <div class="h-4 w-4 bg-orange rounded-full" />
    {/if}
    <div class="h-12 w-12">
        <img class="rounded-full" src={partner.profilePicture} alt="User avatar" referrerpolicy="no-referrer" />
    </div>
    <div class="flex flex-col break-words flex-wrap">
        <div class="flex-1 w-52 font-bold">{partner.firstName} {partner.lastName}</div>
        <div class="flex-1 w-52">{partner.email}</div>
    </div>
</div>
