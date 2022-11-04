<script lang="ts">
    export let clickHandler: () => void;
</script>

<button
    class="button-bg-primary-color button-border-primary-color button-text-primary-color
        text-xl rounded-full py-2 px-12 hover:opacity-90 transition ease-in-out delay-100
        focus:ring focus:ring-[#d0d9ba]"
    on:click={clickHandler}
>
    <slot />
</button>

<style>
    .button-bg-primary-color {
        background-color: var(--pickle);
    }

    .button-border-primary-color {
        border: 2px solid var(--pine);
    }

    .button-text-primary-color {
        color: var(--white);
    }
</style>
