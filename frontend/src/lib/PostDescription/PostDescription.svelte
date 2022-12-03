<script>
    export let value;
    export let maxLength = 0;

    $: {
        if (value !== null && value.length > maxLength) {
            value = value.slice(0, maxLength);
        }
    }
</script>

<div class="bg-ivory mt-2 mx-1.5">
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
