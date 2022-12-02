<script lang="ts">
    import { goto } from '@roxi/routify';

    export let data;
    export let chatroomId: number;
</script>

<div
    class="rounded-2xl m-2 p-2 justify-around flex flex-row bg-olive text-cocoa items-center"
    on:click={$goto(`/chat/${chatroomId}`)}
    on:keydown={$goto(`/chat/${chatroomId}`)}
>
    <div class="h-12 w-12">
        <img src={data.profilePicture} alt="User avatar" referrerpolicy="no-referrer" />
    </div>
    <div class="flex flex-col break-words flex-wrap">
        <div class="flex-1 w-52 font-bold">{data.firstName} {data.lastName}</div>
        <div class="flex-1 w-52">{data.email}</div>
    </div>
</div>
