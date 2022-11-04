<script lang="ts">
    import Footer from '../lib/Footer/Footer.svelte';
    import Button from '../lib/Button/Button.svelte';
</script>

<h2>index</h2>
<a href="/users" target="_self">click link</a>
<Button clickHandler={() => console.log('works')}>Zapisuję się!</Button>
<Footer pageType="main" />