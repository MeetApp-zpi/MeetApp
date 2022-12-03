<script>
    export let value = null;

    const validateInput = () => {
        if (value !== null && !/^$|^\d+$/.test(value)) {
            value = value.slice(0, value.length - 1);
        }
    };
</script>

<input
    min="0"
    id="post-name"
    class="bg-ivory text-gray 
px-2 py-3 text-2xl rounded-lg w-2/3 block
focus:outline-none focus:border-tea focus:ring-1 focus:ring-tea focus:text-cocoa"
    placeholder="Limit osób"
    bind:value
    on:input={validateInput}
    required
/>
