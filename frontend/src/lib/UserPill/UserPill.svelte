<script lang="ts">
    import { goto } from '@roxi/routify';

    export let data;
</script>

<div
    class="rounded-2xl m-2 p-2 justify-around flex flex-row bg-olive text-cocoa items-center hover:cursor-pointer"
    on:click={() => $goto(`/profile/${data.id}`)}
    on:keydown={() => $goto(`/profile/${data.id}`)}
>
    <div class="h-12 w-12">
        <img class="rounded-full" src={data.profilePicture} alt="User avatar" referrerpolicy="no-referrer" />
    </div>
    <div class="flex flex-col">
        <div class="font-bold">{data.firstName} {data.lastName}</div>
        <div class="">{data.email}</div>
    </div>
</div>
