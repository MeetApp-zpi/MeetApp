<script>
    export let value;
</script>

<div class="bg-ivory mt-2 mr-2 ml-2 p-2">
    <textarea
        id="post-name"
        class="bg-ivory border-2 border-pickle text-gray 
    px-2 py-3 text-2xl rounded-lg w-full block
    focus:outline-none focus:border-pickle focus:ring-1 focus:ring-pickle focus:text-cocoa"
        rows="7"
        placeholder="Opis"
        required
        bind:value
    />
</div>
