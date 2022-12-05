<script>
    export let value = null;
</script>

<input
    type="text"
    onfocus="(this.type = 'time')"
    id="post-person-limit"
    class="bg-ivory border-2 border-tea text-gray 
px-2 py-3 text-2xl rounded-lg w-32 block
focus:outline-none focus:border-tea focus:ring-1 focus:ring-tea focus:text-cocoa"
    placeholder="Godzina"
    bind:value
    required
/>
