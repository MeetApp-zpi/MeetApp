<script lang="ts">
    import { goto } from '@roxi/routify';

    export let data;
</script>

<div
    class="rounded-2xl m-2 p-2 justify-around flex flex-row bg-olive text-cocoa items-center"
    on:click={() => $goto(`users/${data.id}`)}
    on:keydown={() => $goto(`users/${data.id}`)}
>
    <div class="h-12 w-12">
        <img src={data.profilePicture} alt="User avatar" referrerpolicy="no-referrer" />
    </div>
    <div class="flex flex-col">
        <div class="font-bold">{data.firstName} {data.lastName}</div>
        <div class="">{data.email}</div>
    </div>
</div>
