<script>
    export let value = null;
</script>

<input
    type="text"
    onfocus="(this.type = 'date')"
    id="post-date"
    class="bg-ivory border-2 border-tea text-gray 
px-2 py-3 text-2xl rounded-lg w-40 block
focus:outline-none focus:border-tea focus:ring-1 focus:ring-tea focus:text-cocoa"
    placeholder="Data"
    bind:value
    required
/>
