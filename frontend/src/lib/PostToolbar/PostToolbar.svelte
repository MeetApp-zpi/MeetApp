<script lang="ts">
    import MdDelete from 'svelte-icons/md/MdDelete.svelte';
    import MdEdit from 'svelte-icons/md/MdEdit.svelte';
    import MdPeople from 'svelte-icons/md/MdPeople.svelte';
</script>

<div class="flex flex-row -mt-4 justify-end mr-4">
    <div class="bg-olive w-10 h-10 rounded-b-lg p-2">
        <MdPeople />
    </div>
    <div class="bg-olive mx-4 w-10 h-10 rounded-b-lg p-2">
        <MdEdit />
    </div>
    <div class="bg-olive w-10 h-10 rounded-b-lg p-2">
        <MdDelete />
    </div>
</div>
